import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import PostList from '../pages/PostList';
import PostDetail from '../pages/PostDetail';

import SignUp from "../pages/SignUp";
import PostForm from "../pages/PostForm";
import Login from "../pages/Loginpage";
import MyPage from "../pages/MyPage";
import RegisterPage from "../pages/RegisterPage";
import Chat from "../pages/Chat";
import PostCategorySidebar from "../post/PostCatergorySidebar";

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
                <Route path="/chat" element={<Chat/>}/>
                <Route path='/posts/category' element={<PostCategorySidebar />} />
            </Routes>
        </Router>
    );
}