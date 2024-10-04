import { Home } from './pages/home'

function App() {
  return (
    <div
      style={{
        backgroundImage:
          "url('https://cdn.dribbble.com/users/979555/screenshots/3375963/media/68e340acc70642614713b63652a2db8c.gif')",
        backgroundRepeat: 'no-repeat',
        backgroundPosition: 'center',
        backgroundSize: 'cover',
        width: '100vw',
        height: '100vh',
        fontFamily: 'Pixelify Sans',
        paddingTop: '75px',
        justifyContent: 'center',
        justifySelf: 'center',
        alignItems: 'center',
      }}
    >
      <Home />
    </div>
  )
}

export default App
