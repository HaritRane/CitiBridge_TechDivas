import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { TableCell, TableRow } from '@mui/material';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';

import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';


const FileArchive = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    // Replace with your API endpoint
    axios.get('http://localhost:8080/files/allFiles')
      .then(response => {
        setData(response.data);
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
  }, []);

  return (
    <div>
      <h1>FILES</h1>
      {/* <table>
        <thead>
          <tr>
            <th>File Name</th>
            <th>Path</th>
           
          </tr>
        </thead>
        <tbody>
          {data.map(item => (
            <tr key={item.id}>
              <td>{item.fileName}</td>
              <td>{item.filePath}</td>
             
            </tr>
          ))}
        </tbody>
      </table> */}
      <Paper sx={{ width: '100%', overflow: 'hidden'}}>
      <TableContainer sx={{ maxHeight: 440 }}>
        <Table stickyHeader aria-label="sticky table">
          <TableHead >
            <TableRow>
             
                <TableCell
                >
                  File Name
                </TableCell>
                <TableCell>File Path</TableCell>
             
            </TableRow>
          </TableHead>
          <TableBody>
          {data.map(item => (
            <TableRow key={item.id} hover role="checkbox" tabIndex={-1}>
              <TableCell>{item.fileName}</TableCell>
              <TableCell>
                {item.filePath}</TableCell>
             
            </TableRow>
          ))}
          </TableBody>
        </Table>
      </TableContainer>
      
    </Paper>
    </div>
  );
};

export default FileArchive;
