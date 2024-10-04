import axios from 'axios'

// const API_URL = process.env.REACT_APP_API_URL
const API_URL = 'http://localhost:8080'
export const subscribe = async (user) => {
    return await axios.post(`${API_URL}/users/signup`, user)
}