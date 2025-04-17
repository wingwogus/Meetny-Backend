import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import PostList from '../pages/PostList';
import PostDetail from '../pages/PostDetail';
import SignUp from "../pages/SignUp";
import Login from "../pages/Login";
import PostForm from "../pages/PostForm";

export default function AppRouter() {
    return (
        <Router>
            <Routes>
                <Route path="/posts" element={<PostList />} />
                <Route path="/posts/:id" element={<PostDetail />} />
                <Route path="/signup" element={<SignUp />} />
                <Route path="/login" element={<Login />} />
                <Route path="/posts/form" element={<PostForm />} />
            </Routes>
        </Router>
    );
}