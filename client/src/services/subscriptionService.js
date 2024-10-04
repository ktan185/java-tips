import axios from 'axios'

export const subscribe = async (user) => {
    return await axios.post(`/users/signup`, user)
}