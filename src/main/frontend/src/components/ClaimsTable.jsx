export default function ClaimsTable({ claims, onDelete }) {
  if (claims.length === 0) {
    return <div className="empty">No claims found.</div>
  }

  const statusClass = (status) => {
    switch (status.toLowerCase()) {
      case 'approved': return 'approved'
      case 'denied': return 'denied'
      case 'pending': return 'pending'
      case 'under review': return 'review'
      default: return ''
    }
  }

  const formatCurrency = (amount) =>
    new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(amount)

  return (
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Claimant</th>
          <th>Type</th>
          <th>Status</th>
          <th>Amount</th>
          <th>Submitted</th>
          <th>Description</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        {claims.map((claim) => (
          <tr key={claim.id}>
            <td><strong>{claim.id}</strong></td>
            <td>{claim.claimant}</td>
            <td>{claim.type}</td>
            <td>
              <span className={`badge ${statusClass(claim.status)}`}>
                {claim.status}
              </span>
            </td>
            <td>{formatCurrency(claim.amount)}</td>
            <td>{claim.dateSubmitted}</td>
            <td>{claim.description}</td>
            <td>
              <button onClick={() => onDelete(claim.id)} style={{ fontSize: 12, padding: '4px 8px' }}>
                ✕
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  )
}
