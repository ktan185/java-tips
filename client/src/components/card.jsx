"use client";
import Card from 'react-bootstrap/Card';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import styles from './card.module.css';
import animation from '../pages/home.module.css';

export function SubscriptionCard() {
  return (
    <div className={`${styles.cardContainer} ${animation.fadeIn}`}>
      <Card className={styles.card}>
        <Card.Body>
          <Form>
            <div className={styles.fields}>
              <Form.Group controlId="formBasicEmail">
                <Form.Control type="email" placeholder="Email Address" />
              </Form.Group>
              <Form.Group controlId="formBasicFirstName">
                <Form.Control type="text" placeholder="First Name" />
              </Form.Group>
              <Form.Group controlId="formBasicLastName">
                <Form.Control type="text" placeholder="Last Name" />
              </Form.Group>

                <Button variant="secondary" type="submit" className={styles.button}>
                    Subscribe!
                </Button>
            </div>  
          </Form>
        </Card.Body>
      </Card>
    </div>
  );
}
