import { useState } from 'react'

export default function ClaimForm({ onSubmit }) {
  const [form, setForm] = useState({
    claimant: '',
    type: 'Medical',
    status: 'Pending',
    amount: '',
    dateSubmitted: new Date().toISOString().split('T')[0],
    description: '',
  })

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value })
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    onSubmit({ ...form, amount: parseFloat(form.amount) || 0 })
  }

  return (
    <form onSubmit={handleSubmit} style={{ marginBottom: 24, padding: 16, background: '#fafafa', borderRadius: 4 }}>
      <h3 style={{ marginBottom: 12, fontSize: 16 }}>New Claim</h3>

      <div className="form-row">
        <div className="form-group">
          <label>Claimant</label>
          <input name="claimant" value={form.claimant} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Type</label>
          <select name="type" value={form.type} onChange={handleChange}>
            <option>Medical</option>
            <option>Dental</option>
            <option>Vision</option>
            <option>Short-Term Disability</option>
            <option>Long-Term Disability</option>
            <option>Life Insurance</option>
          </select>
        </div>
      </div>

      <div className="form-row">
        <div className="form-group">
          <label>Amount ($)</label>
          <input name="amount" type="number" step="0.01" value={form.amount} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Date Submitted</label>
          <input name="dateSubmitted" type="date" value={form.dateSubmitted} onChange={handleChange} required />
        </div>
      </div>

      <div className="form-row">
        <div className="form-group">
          <label>Description</label>
          <textarea name="description" rows={2} value={form.description} onChange={handleChange} required />
        </div>
      </div>

      <button type="submit" className="primary">Submit Claim</button>
    </form>
  )
}
