
"use client"
import Card from 'react-bootstrap/Card'
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import styles from './card.module.css'


export function SubscriptionCard() {
  return (
    <div className={styles.cardContainer}>
      <Card style={{ width: '18rem' }} className={styles.card}>
        <Card.Body>
        <Form>
      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>Email address</Form.Label>
        <Form.Control type="email" placeholder="E.g. ilovejava@gmail.com" />
        <Form.Text className="text-muted">
          <i>We'll never share your email with anyone else.</i>
        </Form.Text>
      </Form.Group>

      <div className={styles.userName}>
          <Form.Group className="mb-3" controlId="formBasicPassword">
            <Form.Label>First Name</Form.Label>
            <Form.Control type="text" placeholder="E.g. John" />
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicPassword">
            <Form.Label>Last Name</Form.Label>
            <Form.Control type="text" placeholder="E.g. Doe" />
          </Form.Group>
      </div>
      <div className={styles.button}>
      <Button variant="primary" type="submit" >
        Subscribe!
      </Button>
      </div>
    </Form>
        </Card.Body>
      </Card>
    </div>
  )
}
