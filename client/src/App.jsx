import { useState } from 'react'
import './App.css'
import { SubscriptionCard } from './components/card'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <h1>Daily Java Tips</h1>
      <div className="card">
        <p>Sign up to our mailing list to receive tips about the Java programming language daily!</p>
          <SubscriptionCard />
      </div>
    </>
  )
}

export default App
