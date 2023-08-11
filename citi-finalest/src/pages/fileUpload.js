// src/components/FileUpload.js
import React, { useState,useContext } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { FileContext } from "../context/FileContext";
import { Button } from "@mui/material";
import "./fileUpload.css";
const customAxios = axios.create({
    // Your custom Axios instance with specific configurations
    validateStatus: function (status) {
      return status >= 200 && status <= 417; // Include any status codes you want to treat as successful responses
    },
  });

const FileUpload = () => {
  const [file, setFile] = useState(null);
  const [responseMessage, setResponseMessage] = useState("");
  const [isButtonEnabled, setIsButtonEnabled] = useState(false);
  const navigate = useNavigate();
  const {setFileName} = useContext(FileContext);

  const onFileChange = (event) => {
    setFile(event.target.files[0]);
   
  };

  const onFileUpload = () => {
    const formData = new FormData();
    formData.append("file", file);

    customAxios
      .post("http://localhost:8080/files/upload", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        setResponseMessage(response.data.message);
        setFileName(file.name);
        if(response.data.message==="File upload done"){
          setIsButtonEnabled(true);
        }
        // navigate("/transaction-list",{ fileName: file.name });
      })
      .catch((error) => {
        console.error("Error uploading file:", error);
      });
  };

  return (
    <div className="form-container">
      <div class="centered-div">
      <div className="input-group">
        <label  htmlFor="fileInput">Upload File:</label>
      <input type="file" onChange={onFileChange} />
      </div>
     
      <Button variant="contained" onClick={onFileUpload} disabled={!file}>
        Upload File
      </Button>
      {responseMessage && <p> {responseMessage}</p>}
      {isButtonEnabled && (
        <Button  variant="contained" color="success" onClick={() => navigate("/transaction-list")}>See Results</Button>
      )}
      
      </div>
    </div>
  );
};

export default FileUpload;
