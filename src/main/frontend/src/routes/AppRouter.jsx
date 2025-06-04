import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import PostList from '../pages/PostList';
import PostDetail from '../pages/PostDetail';
import PostForm from "../pages/PostForm";
import Login from "../pages/Loginpage";
import MyPage from "../pages/MyPage";
import RegisterPage from "../pages/RegisterPage";
import ReviewDetail from "../pages/ReviewDetail";
import ReviewForm from "../pages/ReviewForm";
import MainPage from "../pages/MainPage";
import Chat from "../pages/Chat";
import TermsAgreement from "../pages/TermsAgreement";
import Registration from "../pages/Registration";

export default function AppRouter() {
    return (
        <Router>
            <Routes>
                <Route path="/posts" element={<PostList />} />
                <Route path="/posts/:id" element={<PostDetail />} />
                <Route path="/login" element={<Login />} />
                <Route path="/posts/form" element={<PostForm />} />
                <Route path="/information" element={<MyPage />} />
                <Route path="/agree" element={<TermsAgreement />} />
                <Route path="/register" element={<RegisterPage/>}/>
                <Route path="/reviews/:id" element={<ReviewDetail />} />
                <Route path="/reviews/write/:postId" element={<ReviewForm />} />
                <Route path="/main" element={<MainPage />} />
                <Route path="/chat" element={<Chat/>}/>
                <Route path="/terms" element={<Registration />} />
            </Routes>
        </Router>
    );
}