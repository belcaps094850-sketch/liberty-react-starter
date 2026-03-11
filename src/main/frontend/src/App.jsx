import { useState, useEffect } from 'react'
import ClaimsTable from './components/ClaimsTable'
import ClaimForm from './components/ClaimForm'

const API_BASE = import.meta.env.DEV ? '/api' : './api'

export default function App() {
  const [claims, setClaims] = useState([])
  const [health, setHealth] = useState(null)
  const [error, setError] = useState(null)
  const [showForm, setShowForm] = useState(false)
  const [loading, setLoading] = useState(true)

  const fetchClaims = async () => {
    try {
      const res = await fetch(`${API_BASE}/claims`)
      if (!res.ok) throw new Error(`HTTP ${res.status}`)
      const data = await res.json()
      setClaims(data)
      setError(null)
    } catch (err) {
      setError(`Failed to load claims: ${err.message}`)
    } finally {
      setLoading(false)
    }
  }

  const checkHealth = async () => {
    try {
      const res = await fetch(`${API_BASE}/health`)
      const data = await res.json()
      setHealth(data)
    } catch {
      setHealth({ status: 'DOWN' })
    }
  }

  const createClaim = async (claim) => {
    try {
      const res = await fetch(`${API_BASE}/claims`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(claim),
      })
      if (!res.ok) throw new Error(`HTTP ${res.status}`)
      setShowForm(false)
      fetchClaims()
    } catch (err) {
      setError(`Failed to create claim: ${err.message}`)
    }
  }

  const deleteClaim = async (id) => {
    if (!confirm(`Delete claim ${id}?`)) return
    try {
      const res = await fetch(`${API_BASE}/claims/${id}`, { method: 'DELETE' })
      if (!res.ok) throw new Error(`HTTP ${res.status}`)
      fetchClaims()
    } catch (err) {
      setError(`Failed to delete claim: ${err.message}`)
    }
  }

  useEffect(() => {
    fetchClaims()
    checkHealth()
  }, [])

  return (
    <div className="app">
      <h1>
        Claims Portal <small>Open Liberty + React</small>
      </h1>

      <div className="status-bar">
        <span>
          <span className={`dot ${health?.status === 'UP' ? 'green' : 'red'}`} />
          API: {health?.status || 'Checking...'}
        </span>
        <span>Claims: {claims.length}</span>
        {health?.timestamp && (
          <span>Server time: {new Date(health.timestamp).toLocaleTimeString()}</span>
        )}
      </div>

      {error && <div className="error">{error}</div>}

      <div className="actions">
        <button className="primary" onClick={() => setShowForm(!showForm)}>
          {showForm ? 'Cancel' : '+ New Claim'}
        </button>
        <button onClick={fetchClaims}>Refresh</button>
      </div>

      {showForm && <ClaimForm onSubmit={createClaim} />}

      {loading ? (
        <div className="empty">Loading claims...</div>
      ) : (
        <ClaimsTable claims={claims} onDelete={deleteClaim} />
      )}
    </div>
  )
}
