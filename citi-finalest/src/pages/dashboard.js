import React, {useState,useEffect} from 'react';
import axios from "axios";
import { Link } from 'react-router-dom';
import { useTheme } from '@mui/material/styles';
import { Grid, Container, Typography } from '@mui/material';
import AppWebsiteVisits from '../components/dashboard/AppWebsiteVisits'; 
import AppWidgetSummary from '../components/dashboard/AppWidgetSummary';
import AppCurrentVisits from '../components/dashboard/AppCurrentVisits';
import AppConversionRates from '../components/dashboard/AppConversionRates';
// components
const DashboardPage = () => {
  const theme = useTheme();
  const [filesUploaded, setFilesUploaded] = useState(0);
  const [allCount, setAllCount] = useState(0);
  const [validFailCount, setValidFailCount] = useState(0);
  const [screenPassCount, setScreenPassCount] = useState(0);
  const [screenFailCount, setScreenFailCount] = useState(0);
  const [allCountToday, setAllCountToday] = useState(0);
  const [validFailCountToday, setValidFailCountToday] = useState(0);
  const [screenPassCountToday, setScreenPassCountToday] = useState(0);
  const [screenFailCountToday, setScreenFailCountToday] = useState(0);
  const [chartData, setChartData] = useState([]);

  useEffect(() => {
    // Fetch the number of files uploaded from your API
    axios.get('http://localhost:8080/files/countFiles')
      .then(response => {
        setFilesUploaded(response.data);
      })
      .catch(error => {
        console.error('Error fetching file count:', error);
      });
  }, []);
  useEffect(() => {
    // Fetch the number of files uploaded from your API
    axios.get('http://localhost:8080/transactions/all-count')
      .then(response => {
        setAllCount(response.data);
      })
      .catch(error => {
        console.error('Error fetching transaction count:', error);
      });
  }, []);
  useEffect(() => {
    // Fetch the number of files uploaded from your API
    axios.get('http://localhost:8080/transactions/screen-fail-count')
      .then(response => {
        setScreenFailCount(response.data);
      })
      .catch(error => {
        console.error('Error fetching file count:', error);
      });
  }, []);
  useEffect(() => {
    // Fetch the number of files uploaded from your API
    axios.get('http://localhost:8080/transactions/valid-fail-count')
      .then(response => {
        setValidFailCount(response.data);
      })
      .catch(error => {
        console.error('Error fetching file count:', error);
      });
  }, []);
  useEffect(() => {
    // Fetch the number of files uploaded from your API
    axios.get('http://localhost:8080/transactions/screen-pass-count')
      .then(response => {
        setScreenPassCount(response.data);
      })
      .catch(error => {
        console.error('Error fetching file count:', error);
      });
  }, []);
  useEffect(() => {
    // Fetch the number of files uploaded from your API
    axios.get('http://localhost:8080/transactions/all-count-today')
      .then(response => {
        setAllCountToday(response.data);
      })
      .catch(error => {
        console.error('Error fetching file count:', error);
      });
  }, []);
  useEffect(() => {
    // Fetch the number of files uploaded from your API
    axios.get('http://localhost:8080/transactions/screen-pass-count-today')
      .then(response => {
        setScreenPassCountToday(response.data);
      })
      .catch(error => {
        console.error('Error fetching file count:', error);
      });
  }, []);
  useEffect(() => {
    // Fetch the number of files uploaded from your API
    axios.get('http://localhost:8080/transactions/screen-fail-count-today')
      .then(response => {
        setScreenFailCountToday(response.data);
      })
      .catch(error => {
        console.error('Error fetching file count:', error);
      });
  }, []);
  useEffect(() => {
    // Fetch the number of files uploaded from your API
    axios.get('http://localhost:8080/transactions/valid-fail-count-today')
      .then(response => {
        setValidFailCountToday(response.data);
      })
      .catch(error => {
        console.error('Error fetching file count:', error);
      });
  }, []);

  useEffect(() => {
    // Replace 'YOUR_DROPDOWN_API_ENDPOINT' with the actual API endpoint to fetch the dropdown options
    axios
      .get("http://localhost:8080/keywords")
      .then((response) => {
        const transformedData = response.data.map(item => ({
          label: item.name,
          value: item.occurrences,
        }));
        setChartData(transformedData); // Update the dropdownOptions state with the fetched data
      })
      .catch((error) => {
        console.error("Error fetching keywords", error);
      });
  }, []);

  return (
    <Container maxWidth="xl">
      <Grid container spacing={3}>
          <Grid item xs={12} sm={6} md={3}>
            <AppWidgetSummary title="Files Uploaded" total={filesUploaded} icon={'ant-design:file-filled'} />
          </Grid>

          <Grid item xs={12} sm={6} md={3}>
            <AppWidgetSummary title="Total Transactions" total={allCount} color="info" icon={'ant-design:apple-filled'} />
          </Grid>

          <Grid item xs={12} sm={6} md={3}>
            <AppWidgetSummary title="Total Screening Failed Transactions" total={screenFailCount} color="warning" icon={'ant-design:windows-filled'} />
          </Grid>

          <Grid item xs={12} sm={6} md={3}>
            <AppWidgetSummary title="Total Validation Fail Transactions" total={validFailCount} color="error" icon={'ant-design:bug-filled'} />
          </Grid>
          

          <Grid item xs={12} md={6} lg={8}>
            <AppConversionRates
              title="Most frequently used keywords"
              
              chartData={chartData}
            />
          </Grid>
          <Grid item xs={12} md={6} lg={4}>
            <AppCurrentVisits
              title="Today"
              subheader={`Total transactions: ${allCountToday} `}
              chartData={[
                { label: 'Screening Pass Transactions', value: screenPassCountToday },
                { label: 'Validation Fail Transactions', value: validFailCountToday },
                { label: 'Screening Fail Transactions', value: screenFailCountToday },
              ]}
              chartColors={[
                theme.palette.primary.main,
                theme.palette.info.main,
                theme.palette.warning.main,
                theme.palette.error.main,
              ]}
            />
          </Grid>
    </Grid>
    </Container>
  );
};

export default DashboardPage;
