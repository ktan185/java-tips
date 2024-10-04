import { SubscriptionCard } from "../components/card"
import styles from './home.module.css'

export const Home = () => {
    return (
        <div className={styles.body}>
            <h1>Daily Java Tips</h1>
            <div>
                <p><i>Sign up to our mailing list to receive tips about the Java programming language daily!</i></p>
                {/* <SubscriptionCard /> */}
            </div>
        </div>
    )
}