import {Route, Routes} from 'react-router-dom';

function App() {
    return (
        <div className="App">
            <Routes>
                <Route path="/" element={
                    <h1>main</h1>
                }/>
                <Route path="/app" element={
                    <h1>app</h1>
                }/>
            </Routes>
        </div>
    );
}

export default App;
