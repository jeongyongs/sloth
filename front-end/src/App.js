import {Navigate, Route, Routes} from 'react-router-dom';
import LoginPage from "./pages/LoginPage";
import SignupPage from "./pages/SignupPage";
import NotFoundPage from "./pages/NotFoundPage";
import {useState} from "react";
import AppLayout from "./components/AppLayout";

function App() {

    const [token, setToken] = useState("");

    return (
        <div className="App">
            <Routes>
                <Route path="/" element={<Navigate to="/login"/>}/>
                <Route path="/login" element={<LoginPage token={token} setToken={setToken}/>}/>
                <Route path="/signup" element={<SignupPage token={token}/>}/>
                <Route path="/teams/:teamId/*" element={<AppLayout token={token} setToken={setToken}/>}/>
                <Route path="/*" element={<NotFoundPage/>}/>
            </Routes>
        </div>
    );
}

export default App;
