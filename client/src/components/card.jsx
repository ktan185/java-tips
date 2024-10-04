'use client'
import { useState, useEffect } from 'react'
import Card from 'react-bootstrap/Card'
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import Alert from 'react-bootstrap/Alert' // Import Alert for error messages
import styles from './card.module.css'
import animation from '../pages/home.module.css'
import { subscribe } from '../services/subscriptionService'

export function SubscriptionCard() {
  const [email, setEmail] = useState('')
  const [firstName, setFirstName] = useState('')
  const [lastName, setLastName] = useState('')
  const [subscribed, setSubscribed] = useState(false)
  const [conflict, setConflict] = useState(false)

  const handleSubmit = async (event) => {
    event.preventDefault()
    const user = { email, firstName, lastName }

    try {
      await subscribe(user)
      setEmail('')
      setFirstName('')
      setLastName('')
      setSubscribed(true)
      setConflict(false)
    } catch (err) {
      setConflict(true)
      console.error('Subscription failed:', err.message)
    }
  }

  useEffect(() => {
    if (conflict) {
      const timer = setTimeout(() => {
        setConflict(false)
      }, 5000)
      return () => clearTimeout(timer)
    }
  }, [conflict])

  return !subscribed ? (
    <div className={`${styles.cardContainer} ${animation.fadeIn}`}>
      <Card className={styles.card}>
        <Card.Body>
          <Form onSubmit={handleSubmit}>
            <div className={styles.fields}>
              <Form.Group controlId="formBasicEmail">
                <Form.Control
                  type="email"
                  placeholder="Email Address"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                />
              </Form.Group>

              <Form.Group controlId="formBasicFirstName">
                <Form.Control
                  type="text"
                  placeholder="First Name"
                  value={firstName}
                  onChange={(e) => setFirstName(e.target.value)}
                  required
                />
              </Form.Group>

              <Form.Group controlId="formBasicLastName">
                <Form.Control
                  type="text"
                  placeholder="Last Name"
                  value={lastName}
                  onChange={(e) => setLastName(e.target.value)}
                  required
                />
              </Form.Group>

              <Button
                variant="secondary"
                type="submit"
                className={styles.button}
              >
                Subscribe!
              </Button>
            </div>
          </Form>
          {conflict && (
            <div className={styles.alert}>
              <Alert variant="danger">
                The email has already subscribed! Please use another email!
              </Alert>
            </div>
          )}
        </Card.Body>
      </Card>
    </div>
  ) : (
    <div
      className={`${styles.cardContainer} ${subscribed ? styles.visible : ''}`}
    >
      <p>You have successfully subscribed!</p>
    </div>
  )
}
