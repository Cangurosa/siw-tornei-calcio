import { useState, useEffect } from 'react'
import './App.css'

function App() {
  const [currentUser, setCurrentUser] = useState(null)
  const [loadingAuth, setLoadingAuth] = useState(true)
  const [arbitri, setArbitri] = useState([])
  const [loadingArbitri, setLoadingArbitri] = useState(false)
  const [editMode, setEditMode] = useState(false)
  const [formData, setFormData] = useState({ id: null, nome: '', cognome: '', codiceArbitrale: '' })
  
  // Notification states
  const [successMessage, setSuccessMessage] = useState('')
  const [errorMessage, setErrorMessage] = useState('')

  // Delete modal states
  const [showDeleteModal, setShowDeleteModal] = useState(false)
  const [refereeToDelete, setRefereeToDelete] = useState(null)

  // Login form states
  const [loginUsername, setLoginUsername] = useState('')
  const [loginPassword, setLoginPassword] = useState('')
  const [loginError, setLoginError] = useState('')
  const [loginLoading, setLoginLoading] = useState(false)

  useEffect(() => {
    checkAuth()
  }, [])

  const checkAuth = async () => {
    try {
      const res = await fetch('/api/utente/corrente')
      if (res.ok) {
        const data = await res.json()
        setCurrentUser(data)
        fetchArbitri()
      } else {
        setCurrentUser(null)
      }
    } catch (err) {
      console.error('Errore durante la verifica dell\'autenticazione:', err)
      setCurrentUser(null)
    } finally {
      setLoadingAuth(false)
    }
  }

  const fetchArbitri = async () => {
    setLoadingArbitri(true)
    try {
      const res = await fetch('/api/arbitri')
      if (res.ok) {
        const data = await res.json()
        setArbitri(data)
      } else {
        showNotification('Impossibile caricare gli arbitri.', 'error')
      }
    } catch (err) {
      console.error('Errore di rete durante il recupero degli arbitri:', err)
      showNotification('Errore di connessione durante il caricamento degli arbitri.', 'error')
    } finally {
      setLoadingArbitri(false)
    }
  }

  const showNotification = (message, type) => {
    if (type === 'success') {
      setSuccessMessage(message)
      setErrorMessage('')
      setTimeout(() => setSuccessMessage(''), 4000)
    } else {
      setErrorMessage(message)
      setSuccessMessage('')
      setTimeout(() => setErrorMessage(''), 4000)
    }
  }

  const handleLoginSubmit = async (e) => {
    e.preventDefault()
    setLoginError('')
    if (!loginUsername.trim() || !loginPassword) {
      setLoginError('Inserisci username e password.')
      return
    }

    setLoginLoading(true)
    try {
      const params = new URLSearchParams()
      params.append('username', loginUsername)
      params.append('password', loginPassword)

      // Eseguiamo il POST sulla form login di Spring Security
      const res = await fetch('/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: params.toString()
      })

      // Spring Security reindirizza a / dopo il login corretto o a /login?error su errore.
      // Se il login è fallito, reindirizzerà a una URL che contiene error o non imposterà il cookie di sessione.
      // Eseguiamo quindi una verifica sull'utente corrente.
      const authRes = await fetch('/api/utente/corrente')
      if (authRes.ok) {
        const data = await authRes.json()
        setCurrentUser(data)
        setLoginUsername('')
        setLoginPassword('')
        fetchArbitri()
      } else {
        setLoginError('Credenziali non valide. Riprova.')
      }
    } catch (err) {
      setLoginError('Errore di connessione al server.')
    } finally {
      setLoginLoading(false)
    }
  }

  const handleLogout = async () => {
    try {
      // Spring Security logout POST
      await fetch('/logout', {
        method: 'POST',
        headers: {
          // Solitamente Spring Security richiede una richiesta POST per fare logout
          'Content-Type': 'application/x-www-form-urlencoded',
        }
      })
      setCurrentUser(null)
      setArbitri([])
    } catch (err) {
      console.error('Errore durante il logout:', err)
      // Forziamo comunque il logout sul client
      setCurrentUser(null)
      setArbitri([])
    }
  }

  const handleInputChange = (e) => {
    const { name, value } = e.target
    setFormData(prev => ({ ...prev, [name]: value }))
  }

  const handleRefereeSubmit = async (e) => {
    e.preventDefault()
    
    if (!formData.nome.trim() || !formData.cognome.trim() || !formData.codiceArbitrale.trim()) {
      showNotification('Tutti i campi sono obbligatori.', 'error')
      return
    }

    const url = editMode ? `/api/arbitri/${formData.id}` : '/api/arbitri'
    const method = editMode ? 'PUT' : 'POST'

    try {
      const res = await fetch(url, {
        method: method,
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
      })

      if (res.ok) {
        showNotification(
          editMode ? 'Arbitro aggiornato con successo!' : 'Arbitro creato con successo!',
          'success'
        )
        resetForm()
        fetchArbitri()
      } else if (res.status === 403) {
        showNotification('Azione non consentita: permessi insufficienti.', 'error')
      } else {
        showNotification('Errore durante il salvataggio.', 'error')
      }
    } catch (err) {
      showNotification('Errore di rete durante il salvataggio.', 'error')
    }
  }

  const handleEdit = (arbitro) => {
    setEditMode(true)
    setFormData({
      id: arbitro.id,
      nome: arbitro.nome,
      cognome: arbitro.cognome,
      codiceArbitrale: arbitro.codiceArbitrale
    })
  }

  const triggerDeleteConfirm = (arbitro) => {
    setRefereeToDelete(arbitro)
    setShowDeleteModal(true)
  }

  const handleDelete = async () => {
    if (!refereeToDelete) return
    try {
      const res = await fetch(`/api/arbitri/${refereeToDelete.id}`, {
        method: 'DELETE'
      })

      if (res.ok) {
        showNotification('Arbitro eliminato con successo!', 'success')
        fetchArbitri()
        if (formData.id === refereeToDelete.id) {
          resetForm()
        }
      } else if (res.status === 403) {
        showNotification('Azione non consentita: permessi insufficienti.', 'error')
      } else {
        showNotification('Errore durante l\'eliminazione.', 'error')
      }
    } catch (err) {
      showNotification('Errore di rete durante l\'eliminazione.', 'error')
    } finally {
      setShowDeleteModal(false)
      setRefereeToDelete(null)
    }
  }

  const resetForm = () => {
    setEditMode(false)
    setFormData({ id: null, nome: '', cognome: '', codiceArbitrale: '' })
  }

  if (loadingAuth) {
    return (
      <div className="loader-container" style={{ minHeight: '100vh' }}>
        <div className="spinner"></div>
        <p>Verifica credenziali in corso...</p>
      </div>
    )
  }

  // Se l'utente non è autenticato, mostra il pannello di login
  if (!currentUser) {
    return (
      <div className="login-container animate-fade">
        <div className="login-card">
          <div className="login-logo">
            <i className="fa-solid fa-futbol"></i>
            <h2>TORNEI CALCIO</h2>
            <p className="subtitle">Gestione Arbitri</p>
          </div>

          {loginError && (
            <div className="alert alert--danger slide-in">
              <i className="fa-solid fa-circle-xmark"></i>
              <span>{loginError}</span>
            </div>
          )}

          <form onSubmit={handleLoginSubmit} className="modern-form">
            <div className="form__gruppo">
              <label className="form__etichetta" htmlFor="username">Username</label>
              <div className="input-wrapper">
                <i className="fa-solid fa-user icon-left"></i>
                <input
                  className="form__input"
                  type="text"
                  id="username"
                  value={loginUsername}
                  onChange={(e) => setLoginUsername(e.target.value)}
                  placeholder="Il tuo username"
                  required
                />
              </div>
            </div>

            <div className="form__gruppo">
              <label className="form__etichetta" htmlFor="password">Password</label>
              <div className="input-wrapper">
                <i className="fa-solid fa-lock icon-left"></i>
                <input
                  className="form__input"
                  type="password"
                  id="password"
                  value={loginPassword}
                  onChange={(e) => setLoginPassword(e.target.value)}
                  placeholder="La tua password"
                  required
                />
              </div>
            </div>

            <button type="submit" className="btn btn-primary" style={{ marginTop: '1rem' }} disabled={loginLoading}>
              {loginLoading ? 'Connessione...' : 'Accedi'}
            </button>
          </form>
        </div>
      </div>
    )
  }

  const isAdmin = currentUser.ruolo === 'ADMIN'

  return (
    <div className="app-wrapper">
      {/* Navbar */}
      <header className="navbar">
        <div className="navbar__interno">
          <div className="navbar__logo">
            <i className="fa-solid fa-whistle text-oro"></i>
            <span>TORNEI</span> CALCIO
          </div>
          <nav className="navbar__menu">
            <a href="/" className="navbar__link">Home</a>
            <a href="/tornei" className="navbar__link">Tornei</a>
            <a href="/squadre" className="navbar__link">Squadre</a>
            <a href="/partite" className="navbar__link">Partite</a>
            <span className="navbar__link navbar__link--attivo" style={{ cursor: 'pointer' }}>Arbitri (React)</span>
          </nav>
          <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
            <div className="user-profile">
              <i className="fa-regular fa-user user-icon"></i>
              <div className="user-info">
                <span className="username">{currentUser.username}</span>
                <span className={`user-badge ${isAdmin ? 'badge-admin' : 'badge-user'}`}>
                  {currentUser.ruolo}
                </span>
              </div>
            </div>
            <button onClick={handleLogout} className="btn-logout" title="Disconnetti">
              <i className="fa-solid fa-right-from-bracket"></i>
            </button>
          </div>
        </div>
      </header>

      {/* Main Content Container */}
      <main className="container main-content animate-fade">
        <div className="dashboard-header">
          <div>
            <h1>Gestione Arbitri</h1>
            <p className="subtitle">Sezione interattiva in React + Vite per amministrare gli ufficiali di gara.</p>
          </div>
          <div className="stats-pill">
            <i className="fa-solid fa-users"></i>
            <span>Totale Arbitri: <strong>{arbitri.length}</strong></span>
          </div>
        </div>

        {/* Notifications */}
        {successMessage && (
          <div className="alert alert--success slide-in">
            <i className="fa-solid fa-circle-check"></i>
            <span>{successMessage}</span>
          </div>
        )}
        {errorMessage && (
          <div className="alert alert--danger slide-in">
            <i className="fa-solid fa-circle-xmark"></i>
            <span>{errorMessage}</span>
          </div>
        )}

        <div className="grid griglia-2">
          {/* List Section */}
          <div className="section-card">
            <h2>Lista degli Arbitri Registrati</h2>
            {loadingArbitri ? (
              <div className="loader-container">
                <div className="spinner"></div>
                <p>Caricamento arbitri...</p>
              </div>
            ) : arbitri.length === 0 ? (
              <div className="empty-state">
                <i className="fa-solid fa-folder-open empty-icon"></i>
                <p>Nessun arbitro presente nel sistema.</p>
              </div>
            ) : (
              <div className="referee-list">
                {arbitri.map(a => (
                  <div key={a.id} className="referee-card card">
                    <div className="referee-card-info">
                      <div className="avatar">
                        <i className="fa-solid fa-user-tie"></i>
                      </div>
                      <div className="referee-details">
                        <h3>{a.nome} {a.cognome}</h3>
                        <p className="ref-code">
                          <i className="fa-solid fa-hashtag"></i> Codice: <strong>{a.codiceArbitrale}</strong>
                        </p>
                      </div>
                    </div>
                    {isAdmin && (
                      <div className="ref-actions">
                        <button onClick={() => handleEdit(a)} className="btn-icon-only btn-edit-icon" title="Modifica">
                          <i className="fa-solid fa-pen-to-square"></i>
                        </button>
                        <button onClick={() => triggerDeleteConfirm(a)} className="btn-icon-only btn-delete-icon" title="Elimina">
                          <i className="fa-solid fa-trash-can"></i>
                        </button>
                      </div>
                    )}
                  </div>
                ))}
              </div>
            )}
          </div>

          {/* Form / Admin section */}
          <div className="section-card">
            {isAdmin ? (
              <div className="form-card">
                <h2>{editMode ? 'Modifica Arbitro' : 'Aggiungi Nuovo Arbitro'}</h2>
                <form onSubmit={handleRefereeSubmit} className="modern-form">
                  <div className="form__gruppo">
                    <label className="form__etichetta" htmlFor="nome">Nome</label>
                    <div className="input-wrapper">
                      <i className="fa-solid fa-signature icon-left"></i>
                      <input
                        className="form__input"
                        type="text"
                        id="nome"
                        name="nome"
                        value={formData.nome}
                        onChange={handleInputChange}
                        placeholder="Inserisci il nome"
                        required
                      />
                    </div>
                  </div>

                  <div className="form__gruppo">
                    <label className="form__etichetta" htmlFor="cognome">Cognome</label>
                    <div className="input-wrapper">
                      <i className="fa-solid fa-signature icon-left"></i>
                      <input
                        className="form__input"
                        type="text"
                        id="cognome"
                        name="cognome"
                        value={formData.cognome}
                        onChange={handleInputChange}
                        placeholder="Inserisci il cognome"
                        required
                      />
                    </div>
                  </div>

                  <div className="form__gruppo">
                    <label className="form__etichetta" htmlFor="codiceArbitrale">Codice Arbitrale</label>
                    <div className="input-wrapper">
                      <i className="fa-solid fa-id-card icon-left"></i>
                      <input
                        className="form__input"
                        type="text"
                        id="codiceArbitrale"
                        name="codiceArbitrale"
                        value={formData.codiceArbitrale}
                        onChange={handleInputChange}
                        placeholder="Es. ARB-2026-99"
                        required
                      />
                    </div>
                  </div>

                  <div className="form-actions">
                    <button type="submit" className="btn btn-primary btn-submit-form">
                      <i className="fa-solid fa-floppy-disk"></i> {editMode ? 'Salva Modifiche' : 'Registra Arbitro'}
                    </button>
                    {editMode && (
                      <button type="button" onClick={resetForm} className="btn btn-secondary">
                        Annulla
                      </button>
                    )}
                  </div>
                </form>
              </div>
            ) : (
              <div className="info-card glass-accent">
                <div className="info-icon-container">
                  <i className="fa-solid fa-shield-halved"></i>
                </div>
                <h3>Accesso Sola Lettura</h3>
                <p>I tuoi permessi attuali (ruolo: <strong>{currentUser.ruolo}</strong>) consentono solo la visualizzazione dell'elenco degli arbitri.</p>
                <p className="note">L'aggiunta, la modifica e la cancellazione degli arbitri sono riservate agli account <strong>Amministratore</strong>.</p>
              </div>
            )}
          </div>
        </div>
      </main>

      {/* Confirmation Modal */}
      {showDeleteModal && refereeToDelete && (
        <div className="modal-overlay">
          <div className="modal-card">
            <div className="modal-header">
              <i className="fa-solid fa-triangle-exclamation"></i>
              <h3>Conferma Eliminazione</h3>
            </div>
            <div className="modal-body">
              <p>Sei sicuro di voler eliminare l'arbitro <strong>{refereeToDelete.nome} {refereeToDelete.cognome}</strong> dal sistema?</p>
              <p className="warning-text">Questa operazione rimuoverà definitivamente l'arbitro.</p>
            </div>
            <div className="modal-footer">
              <button onClick={handleDelete} className="btn btn-danger">
                Sì, Elimina
              </button>
              <button onClick={() => setShowDeleteModal(false)} className="btn btn-secondary">
                Annulla
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Footer */}
      <footer className="footer">
        <div className="container">
          <div className="footer__fondo">
            <p>© 2026 Tornei di Calcio Amatoriale. Sistema Informativo su Web.</p>
            <p>Sviluppato con Spring Boot & React.</p>
          </div>
        </div>
      </footer>
    </div>
  )
}

export default App
