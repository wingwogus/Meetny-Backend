import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import PostList from '../pages/PostList';
import PostDetail from '../pages/PostDetail';

import SignUp from "../pages/SignUp";
import PostForm from "../pages/PostForm";
import Login from "../pages/Loginpage";
import MyPage from "../pages/MyPage";
import RegisterPage from "../pages/RegisterPage";
import ReviewDetail from "../pages/ReviewDetail";
import ReviewForm from "../pages/ReviewForm";
import Chat from "../pages/Chat";
import OAuth2RedirectHandler from '../pages/OAuth2RedirectHandler';
import SocialRegisterPage from '../pages/SocialRegisterPage';


export default function AppRouter() {
    return (
        <Router>
            <Routes>
                <Route path="/posts" element={<PostList />} />
                <Route path="/posts/:id" element={<PostDetail />} />
                <Route path="/signup" element={<SignUp />} />
                <Route path="/login" element={<Login />} />
                <Route path="/posts/form" element={<PostForm />} />
                <Route path="/information" element={<MyPage />} />
                <Route path="/register" element={<RegisterPage/>}/>
                <Route path="/reviews/:id" element={<ReviewDetail />} />
                <Route path="/reviews/write/:postId" element={<ReviewForm />} />
                <Route path="/chat" element={<Chat/>}/>
                <Route path="/oauth2/redirect" element={<OAuth2RedirectHandler />} />
                <Route path="/social-register" element={<SocialRegisterPage />} />

            </Routes>
        </Router>
    );
}