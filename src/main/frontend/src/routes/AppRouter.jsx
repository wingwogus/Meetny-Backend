import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import PostList from '../pages/PostList';
import PostDetail from '../pages/PostDetail';

export default function AppRouter() {
    return (
        <Router>
            <Routes>
                <Route path="/posts" element={<PostList />} />
                <Route path="/posts/:id" element={<PostDetail />} />
            </Routes>
        </Router>
    );
}