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
import NavigatorForTest from "./components/NavigatorForTest";

function App() {
    return (
        <div className="App">
            <NavigatorForTest/>
            <Routes>
                <Route path="/" element={<Navigate to="/login"/>}/>
                <Route path="/login" element={<LoginPage/>}/>
                <Route path="/signup" element={<SignupPage/>}/>
                <Route path="/teams/:teamId/dashboard" element={<DashboardPage/>}/>
                <Route path="/teams/:teamId/notification" element={<NotificationPage/>}/>
                <Route path="/teams/:teamId/profile" element={<ProfilePage/>}/>
                <Route path="/teams/:teamId/invites" element={<InvitePage/>}/>
                <Route path="/teams/:teamId" element={<TeamPage/>}/>
                <Route path="/teams/:teamId/handovers" element={<HandoverPage/>}/>
                <Route path="/teams/:teamId/reports" element={<ReportPage/>}/>
                <Route path="/teams/me" element={<Navigate to="/teams/me/dashboard"/>}/>
                <Route path="/teams/me/handovers" element={<Navigate to="/teams/me/dashboard"/>}/>
                <Route path="/teams/me/reports" element={<Navigate to="/teams/me/dashboard"/>}/>
                <Route path="/*" element={<NotFoundPage/>}/>
            </Routes>
        </div>
    );
}

export default App;
