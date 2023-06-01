import {Route, Routes} from 'react-router-dom';
import WelcomePage from "./pages/WelcomePage";
import {useState} from "react";
import AuthPage from "./pages/AuthPage";
import SignUpPage from "./pages/SignUpPage";
import DashboardPage from "./pages/DashboardPage";
import NewMemberPage from "./pages/NewMemberPage";
import NotFoundPage from "./pages/NotFoundPage";
import TeamPage from "./pages/TeamPage";

function App() {

    const [token, setToken] = useState("");

    return (
        <div className="App">
            <Routes>
                <Route path="/" element={
                    <WelcomePage/>
                }/>
                <Route path="/member/auth" element={
                    <AuthPage token={token} setToken={setToken}/>
                }/>
                <Route path="/member/create" element={
                    <SignUpPage/>
                }/>
                <Route path="/app/dashboard" element={
                    <DashboardPage token={token}/>
                }/>
                <Route path="/app/team" element={
                    <TeamPage token={token}/>
                }/>
                <Route path="/member/complete" element={
                    <NewMemberPage/>
                }/>
                <Route path="/*" element={
                    <NotFoundPage/>
                }/>
            </Routes>
        </div>
    );
}

export default App;
