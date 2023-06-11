import {Navigate, Route, Routes} from 'react-router-dom';
import LoginPage from "./pages/LoginPage";
import SignupPage from "./pages/SignupPage";
import NotFoundPage from "./pages/NotFoundPage";
import NotificationPage from "./pages/NotificationPage";
import ProfilePage from "./pages/ProfilePage";
import InvitePage from "./pages/InvitePage";
import TeamPage from "./pages/TeamPage";
import DashboardPage from "./pages/DashboardPage";
import HandoverPage from "./pages/HandoverPage";
import ReportPage from "./pages/ReportPage";
import {useState} from "react";

function App() {

    const [token, setToken] = useState("");

    return (
        <div className="App">
            <Routes>
                <Route path="/" element={<Navigate to="/login"/>}/>
                <Route path="/login" element={<LoginPage token={token} setToken={setToken}/>}/>
                <Route path="/signup" element={<SignupPage token={token}/>}/>
                <Route path="/teams/:teamId/dashboard" element={<DashboardPage token={token}/>}/>
                <Route path="/teams/:teamId/notification" element={<NotificationPage token={token}/>}/>
                <Route path="/teams/:teamId/profile" element={<ProfilePage token={token}/>}/>
                <Route path="/teams/:teamId/invites" element={<InvitePage token={token}/>}/>
                <Route path="/teams/:teamId" element={<TeamPage token={token}/>}/>
                <Route path="/teams/:teamId/handovers" element={<HandoverPage token={token}/>}/>
                <Route path="/teams/:teamId/reports" element={<ReportPage token={token}/>}/>
                <Route path="/teams/me" element={<Navigate to="/teams/me/dashboard"/>}/>
                <Route path="/teams/me/handovers" element={<Navigate to="/teams/me/dashboard"/>}/>
                <Route path="/teams/me/reports" element={<Navigate to="/teams/me/dashboard"/>}/>
                <Route path="/*" element={<NotFoundPage/>}/>
            </Routes>
        </div>
    );
}

export default App;
