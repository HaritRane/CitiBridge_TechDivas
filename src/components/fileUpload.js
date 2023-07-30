// src/components/FileUpload.js
import React, { useState } from "react";
import axios from "axios";

const customAxios = axios.create({
    // Your custom Axios instance with specific configurations
    validateStatus: function (status) {
      return status >= 200 && status <= 417; // Include any status codes you want to treat as successful responses
    },
  });

const FileUpload = () => {
  const [file, setFile] = useState(null);
  const [responseMessage, setResponseMessage] = useState("");

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
      })
      .catch((error) => {
        console.error("Error uploading file:", error);
      });
  };

  return (
    <div>
      <input type="file" onChange={onFileChange} />
      <button onClick={onFileUpload} disabled={!file}>
        Upload File
      </button>
      {responseMessage && <p>Response: {responseMessage}</p>}
    </div>
  );
};

export default FileUpload;
