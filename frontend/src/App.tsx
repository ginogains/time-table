import { Link, Route, Routes } from 'react-router-dom';
import { SubjectsPage } from './pages/SubjectsPage';
import { ClassTimetablePage } from './pages/ClassTimetablePage';
import { GenerateTimetablePage } from './pages/GenerateTimetablePage';
import './App.css';

function App() {
  return (
    <div className="app-shell">
      <header className="app-header">
        <nav className="app-nav">
          <Link to="/">Subjects</Link>
          <Link to="/generate">Generate</Link>
          <Link to="/class-view">Class View</Link>
        </nav>
      </header>
      <main className="app-main">
        <Routes>
          <Route path="/" element={<SubjectsPage />} />
          <Route path="/generate" element={<GenerateTimetablePage />} />
          <Route path="/class-view" element={<ClassTimetablePage />} />
        </Routes>
      </main>
    </div>
  );
}

export default App;
