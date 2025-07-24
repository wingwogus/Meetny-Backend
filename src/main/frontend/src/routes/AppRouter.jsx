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
import ReviewEditForm from "../pages/ReviewEditForm";
import TermsAgreement from "../pages/TermsAgreement";
import Registration from "../pages/Registration";
import PostCategorySidebar from "../post/PostCatergorySidebar";
import Post from "../main/PostMain";
import Write from "../main/PostWrite";
import OAuth2RedirectHandler from '../pages/OAuth2RedirectHandler';
import SocialRegisterPage from '../pages/SocialRegisterPage';


export default function AppRouter() {
    return (
        <Router>
            <Routes>
                <Route path="/posts" element={<Post/>} />
                <Route path="/posts/:id" element={<PostDetail />} />
                <Route path="/login" element={<Login />} />
                <Route path="/posts/form" element={<PostForm />} />
                <Route path="/information" element={<MyPage />} />
                <Route path="/agree" element={<TermsAgreement />} />
                <Route path="/register" element={<RegisterPage/>}/>
                <Route path="/reviews/:id" element={<ReviewDetail />} />
                <Route path="/reviews/write/:postId" element={<ReviewForm />} />
                <Route path="/reviews/edit/:postId" element={<ReviewEditForm />} />
                <Route path="/" element={<MainPage />} />
                <Route path="/chat" element={<Chat/>}/>
                <Route path="/terms" element={<Registration />} />
                <Route path='/posts/category' element={<PostCategorySidebar />} />
                <Route path='/posts/write' element={<Write/>} />
                <Route path="/oauth2/redirect" element={<OAuth2RedirectHandler />} />
                <Route path="/social-register" element={<SocialRegisterPage />} />

            </Routes>
        </Router>
    );
}