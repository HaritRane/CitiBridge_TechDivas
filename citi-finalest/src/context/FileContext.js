// src/context/FileContext.js
import { createContext, useState } from "react";

const FileContext = createContext();

const FileProvider = ({ children }) => {
  const [fileName, setFileName] = useState("");

  return (
    <FileContext.Provider value={{ fileName, setFileName }}>
      {children}
    </FileContext.Provider>
  );
};

export { FileContext, FileProvider };
