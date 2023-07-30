import { BrowserRouter, Routes, Route } from "react-router-dom";
import './App.css';
import FileUpload from './components/fileUpload';

function App() {
  return (
   <div>
    <BrowserRouter>
      <Routes>
        {/* <Route path="/" element={<Layout />}>
          <Route index element={<FileUpload />} />
          <Route path="TransactionList" element={<TransactionList />} /> */}
          <Route path="/" element={<FileUpload/>}>
         
        </Route>
      </Routes>
    </BrowserRouter>
   </div>
  );
}

export default App;
