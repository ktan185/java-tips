"use client";
import { useState } from 'react'
import Card from 'react-bootstrap/Card'
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import styles from './card.module.css'
import animation from '../pages/home.module.css'
import { subscribe } from '../services/subscriptionService'

export function SubscriptionCard() {
  const [email, setEmail] = useState('')
  const [firstName, setFirstName] = useState('')
  const [lastName, setLastName] = useState('')

  const handleSubmit = async (event) => {
    event.preventDefault(); 
    const user = { email, firstName, lastName };

    try {
      subscribe(user);
      setEmail('');
      setFirstName('');
      setLastName('');
    } catch (err) {
      console.error('Subscription failed:', err);
    }
  }

  return (
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
        </Card.Body>
      </Card>
    </div>
  );
}
