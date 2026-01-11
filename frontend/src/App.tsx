import { Link, Route, Routes } from 'react-router-dom';
import { DepartmentPage } from './pages/DepartmentPage';
import { SectionPage } from './pages/SectionPage';
import { WorkingDayPage } from './pages/WorkingDayPage';
import { TimeSlotPage } from './pages/TimeSlotPage';
import { SubjectsPage } from './pages/SubjectsPage';
import { FacultyPage } from './pages/FacultyPage';
import { RoomPage } from './pages/RoomPage';
import { RulesPage } from './pages/RulesPage';
import { GenerateTimetablePage } from './pages/GenerateTimetablePage';
import { SectionTimetablePage } from './pages/SectionTimetablePage';
import { FacultyTimetablePage } from './pages/FacultyTimetablePage';
import { PrintableTimetablePage } from './pages/PrintableTimetablePage';
import './App.css';

function App() {
  return (
    <div className="app-shell">
      <header className="app-header">
        <nav className="app-nav">
          <Link to="/departments">Departments</Link>
          <Link to="/sections">Sections</Link>
          <Link to="/working-days">Working Days</Link>
          <Link to="/time-slots">Periods</Link>
          <Link to="/subjects">Subjects</Link>
          <Link to="/faculty">Faculty</Link>
          <Link to="/rooms">Rooms</Link>
          <Link to="/rules">Rules</Link>
          <Link to="/generate">Generate</Link>
          <Link to="/section-view">Section View</Link>
          <Link to="/faculty-view">Faculty View</Link>
          <Link to="/printable-view">Printable View</Link>
        </nav>
      </header>
      <main className="app-main">
        <Routes>
          <Route path="/departments" element={<DepartmentPage />} />
          <Route path="/sections" element={<SectionPage />} />
          <Route path="/working-days" element={<WorkingDayPage />} />
          <Route path="/time-slots" element={<TimeSlotPage />} />
          <Route path="/subjects" element={<SubjectsPage />} />
          <Route path="/faculty" element={<FacultyPage />} />
          <Route path="/rooms" element={<RoomPage />} />
          <Route path="/rules" element={<RulesPage />} />
          <Route path="/generate" element={<GenerateTimetablePage />} />
          <Route path="/section-view" element={<SectionTimetablePage />} />
          <Route path="/faculty-view" element={<FacultyTimetablePage />} />
          <Route path="/printable-view" element={<PrintableTimetablePage />} />
          <Route path="/" element={<DepartmentPage />} />
        </Routes>
      </main>
    </div>
  );
}

export default App;
