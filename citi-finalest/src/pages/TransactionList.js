// src/components/ListPage.js
import React, { useEffect, useState ,useContext} from "react";
import axios from "axios";
import { FileContext } from "../context/FileContext";
import { Select,MenuItem } from "@mui/material";
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import "./TransactionList.css"
const columns = [
    { id: 'id', label: 'Transaction ID', minWidth: 170 },
    { id: 'date', label: 'Date', minWidth: 100 ,format: (value) => value.toLocaleString('en-US'),},
    {
      id: 'payerAccount',
      label: 'Payer Account Number',
      minWidth:170,
      align: 'right',
      format: (value) => value.toLocaleString('en-US'),
    },
    {
      id: 'payerName',
      label: 'Payer Name',
     minWidth:170,
      align: 'right',
      
    },
    {
      id: 'payeeAccount',
      label: 'Payee Account Number',
      minWidth:170,
      align: 'right',
      //format: (value) => value.toFixed(2),
      format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'payeeName',
        label: 'Payee Name',
        minWidth:170,
        align: 'right',
        //format: (value) => value.toFixed(2),
        
      },
    {
        id: 'amount',
        label: 'Amount',
        
        align: 'right',
        format: (value) => value.toFixed(2),
       
      },
      {
        id: 'status',
        label: 'Status',
        
        align: 'right',
        
       
      },
  ];
const TransactionList = () => {
  const [patchApiResponse, setPatchApiResponse] = useState(null);
  const { fileName } = useContext(FileContext);
  const [selectedOption, setSelectedOption] = useState("Get all transactions"); // Initialize with default option value
  const [data, setData] = useState([]); // State to hold the fetched data
  const [selectedOption1, setSelectedOption1] = useState(fileName);
  const [dropdownOptions, setDropdownOptions] = useState([]);
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  useEffect(() => {
    // Replace 'YOUR_DROPDOWN_API_ENDPOINT' with the actual API endpoint to fetch the dropdown options
    axios
      .get("http://localhost:8080/files/all")
      .then((response) => {
        setDropdownOptions(response.data); // Update the dropdownOptions state with the fetched data
      })
      .catch((error) => {
        console.error("Error fetching dropdown options:", error);
      });
  }, []);
    
  const handleOptionChange = (event) => {
    setSelectedOption(event.target.value);
  };
  const handleOptionChange1 = (event) => {
    setSelectedOption1(event.target.value);
  };

  useEffect(() => {
    console.log(fileName);
    // Perform the PATCH API request here
    // Replace 'YOUR_PATCH_API_ENDPOINT' with the actual endpoint for PATCH request
    axios
      .patch(`http://localhost:8080/transactions/${fileName}/findResult`)
      .then((response) => {
        console.log("PATCH API response:", response.data);
        setPatchApiResponse(response.data);
      })
      .catch((error) => {
        console.error("Error with PATCH API:", error);
      });
  }, []);

   // Function to fetch data based on the selected option
   const fetchData = () => {
    console.log(selectedOption1);
    let query="";
    // Replace 'YOUR_API_ENDPOINT' with the actual API endpoint that corresponds to the selected option
    switch(selectedOption){
         
        case "Get validation failed transactions":
            query=`${selectedOption1}/get-valid-fail-transactions`;
            break;
        case "Get screening failed transactions":
            query=`${selectedOption1}/get-screen-fail-transactions`;
            break;
        case "Get screening passed transactions":
            query=`${selectedOption1}/get-screen-pass-transactions`;
            break;
        default :
            query=`${selectedOption1}/get-all-transactions`;
            break;


    }
    axios
      .get(`http://localhost:8080/transactions/${query}`)
      .then((response) => {
        setData(response.data); // Update the data state with the fetched data
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  };

  useEffect(() => {
    fetchData();
  }, [selectedOption,selectedOption1]);


  return (
    <div>
      <h1>Transactions</h1>
      {/* Display the PATCH API response data */}
     
      {/* Add content for the List page */}
      <div className="drop-down-container">
      <label for="demo1" className="result">Select an option:</label>
      <Select
    className="drop-down"
    labelId="demo-simple-select-label"
    id="demo1"
    value={selectedOption}
    label="ListTransactions"
    onChange={handleOptionChange}
  >
    <MenuItem value="Get all transactions">Get all transactions</MenuItem>
        <MenuItem value="Get validation failed transactions">Get validation failed transactions</MenuItem>
        <MenuItem value="Get screening failed transactions">Get screening failed transactions</MenuItem>
        <MenuItem value="Get screening passed transactions">Get screening passed transactions</MenuItem>
  </Select>
      {/* <select value={selectedOption} onChange={handleOptionChange}>
        <option value="Get all transactions">Get all transactions</option>
        <option value="Get validation failed transactions">Get validation failed transactions</option>
        <option value="Get screening failed transactions">Get screening failed transactions</option>
        <option value="Get screening passed transactions">Get screening passed transactions</option>
       
      </select> */}
<label for="demo2" className="result">Select File</label>
      <Select 
      value={selectedOption1} 
      onChange={handleOptionChange1}
      className="drop-down"
      labelId="demo-simple-select-label"
      id="demo2"
      label="Select File">
      {dropdownOptions.map((option,index) => (
          <MenuItem key={index} value={option} placeholder="Select File">
            {option}
          </MenuItem>
        ))}
      </Select>
      {patchApiResponse &&
      <table className="corner-table">
        
        <tbody>
          {Object.entries(patchApiResponse).map(([key, value]) => (
            <tr key={key}
            className={key === 'validationPass' ? 'validation-pass-row' : key === 'screeningFail' ? 'screening-fail-row' : key === 'validationFail' ? 'validation-fail-row' : key === 'screeningPass' ? 'screening-pass-row' : ''}>
              <th>{key}</th>
              <td>{value}</td>
            </tr>
          ))}
        </tbody>
      </table>
}
      </div>
      <br />
      {/* <div style={{ maxHeight: "300px", overflowY: "auto", overflowX: "auto" }}>
      <table>
        <thead>
         
          <tr>
            <th>Transaction ID</th>
            <th>Payer Account Number</th>
            <th>Payer Name</th>
            <th>Payee Account Number</th>
            <th>Payee Name</th>
            <th>Amount</th>
            <th>Date</th>
            <th>Status</th>
           
          </tr>
        </thead>
        <tbody>
          
          {data.map((item) => (
            <tr key={item.id}>
              <td>{item.id}</td>
              <td>{item.payerAccount}</td>
              <td>{item.payerName}</td>
              <td>{item.payeeName}</td>
              <td>{item.payeeAccount}</td>
              <td>{item.amount}</td>
              <td>{item.date}</td>
              <td>{item.status}</td>
              

             
            </tr>
          ))}
        </tbody>
      </table>
      </div> */}
      <Paper sx={{ width: '100%', overflow: 'hidden'}}>
      <TableContainer sx={{ maxHeight: 440 }}>
        <Table stickyHeader aria-label="sticky table">
          <TableHead >
            <TableRow>
              {columns.map((column) => (
                <TableCell
                  key={column.id}
                  align={column.align}
                  style={{ minWidth: column.minWidth }}
                >
                  {column.label}
                </TableCell>
              ))}
            </TableRow>
          </TableHead>
          <TableBody>
            {data
              .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
              .map((row) => {
                return (
                  <TableRow hover role="checkbox" tabIndex={-1} key={row.code}
                  className={row.status === 'Screening-pass' ? 'screening-pass-row' : row.status === 'Screening-fail' ? 'screening-fail-row' : row.status === 'Validation-fail' ? 'validation-fail-row' : ''}>
                    {columns.map((column) => {
                      const value = row[column.id];
                      return (
                        <TableCell key={column.id} align={column.align}>
                          {column.format && typeof value === 'number'
                            ? column.format(value)
                            : value}
                        </TableCell>
                      );
                    })}
                  </TableRow>
                );
              })}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        rowsPerPageOptions={[10, 25, 100]}
        component="div"
        count={data.length}
        rowsPerPage={rowsPerPage}
        page={page}
        onPageChange={handleChangePage}
        onRowsPerPageChange={handleChangeRowsPerPage}
      />
    </Paper>
    </div>
  );
};

export default TransactionList;
