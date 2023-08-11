import { BrowserRouter, Routes, Route } from "react-router-dom";
import './App.css';
import FileUpload from './pages/fileUpload';
import TransactionList from "./pages/TransactionList";
import FileArchive from "./pages/fileArchive";
import HomePage from "./pages/home";
import { FileProvider } from "./context/FileContext";
import Layout from "./components/Layout";
import DashboardPage from "./pages/dashboard";

function App() {
  
  return (
   <div>
    <BrowserRouter>
    <Layout />
      <Routes>
      <Route  exact path="/" element={<HomePage />} />
          <Route  exact path="/upload" element={<FileUpload />} />
          <Route exact path="/transaction-list" element={<TransactionList />} />
          <Route exact path="/files" element={<FileArchive/>} />
          <Route exact path="/dashboard" element={<DashboardPage/>} />
       
      </Routes>
    </BrowserRouter>
   </div>
  );
}

const AppWithFileProvider = () => {
  return (
    <FileProvider>
      <App />
    </FileProvider>
  );
};
export default AppWithFileProvider;
