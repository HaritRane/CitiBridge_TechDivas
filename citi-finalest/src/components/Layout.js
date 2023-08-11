import * as React from 'react';
import PropTypes from 'prop-types';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import CssBaseline from '@mui/material/CssBaseline';
import Divider from '@mui/material/Divider';
import Drawer from '@mui/material/Drawer';
import IconButton from '@mui/material/IconButton';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import MenuIcon from '@mui/icons-material/Menu';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
const drawerWidth = 240;


function DrawerAppBar(props) {
  const { window } = props;
  const [mobileOpen, setMobileOpen] = React.useState(false);
const navigate = useNavigate();
  const handleDrawerToggle = () => {
    setMobileOpen((prevState) => !prevState);
  };

  const drawer = (
    <Box onClick={handleDrawerToggle} sx={{ textAlign: 'center' }}>
      <Typography variant="h6" sx={{ my: 2 }}>
       Menu
      </Typography>
      <Divider />
      <List>
      <ListItem key= "dashboard" disablePadding>
            <Link to={`/dashboard`}></Link>
            <ListItemButton sx={{ textAlign: 'center' }}>
              <ListItemText primary="Dashboard" />
            </ListItemButton>
          </ListItem>
          <ListItem key= "uplaod-file" disablePadding>
            <Link to={`/upload`}></Link>
            <ListItemButton sx={{ textAlign: 'center' }}>
              <ListItemText primary="Upload File" />
            </ListItemButton>
          </ListItem>
          <ListItem key= "transactions" disablePadding>
            <Link to={`/transaction-list`}></Link>
            <ListItemButton sx={{ textAlign: 'center' }}>
              <ListItemText primary="Transactions" />
            </ListItemButton>
          </ListItem>
          <ListItem key= "file-archive" disablePadding>
            <Link to={`/files`}></Link>
            <ListItemButton sx={{ textAlign: 'center' }}>
              <ListItemText primary="Files" />
            </ListItemButton>
          </ListItem>
       
      </List>
    </Box>
  );

  const container = window !== undefined ? () => window().document.body : undefined;

  return (
    <Box sx={{ display: 'flex' }}>
      <CssBaseline />
      <AppBar component="nav">
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            edge="start"
            onClick={handleDrawerToggle}
            sx={{ mr: 2, display: { sm: 'none' } }}
          >
            <MenuIcon />
          </IconButton>
          <Typography
            variant="h6"
            component="div"
            sx={{ flexGrow: 1, display: { xs: 'none', sm: 'block' } }}
          >
            Sanction Screening of Transactions
          </Typography>
          <Box sx={{ display: { xs: 'none', sm: 'block' } }}>
            
          <Button key="dashboard" sx={{ color: '#fff' }} onClick={()=>navigate("/dashboard")}>
                Dashboard
              </Button>
              <Button key="upload-file" sx={{ color: '#fff' }} onClick={()=>navigate("/upload")}>
                Upload File
              </Button>
              <Button key="transactions" sx={{ color: '#fff' }} onClick={()=>navigate("/transaction-list")}>
                Transactions
              </Button>
              <Button key="file-archive" sx={{ color: '#fff' }} onClick={()=>navigate("/files")}>
                Files
              </Button>
           
          </Box>
        </Toolbar>
      </AppBar>
      <Box component="nav">
        <Drawer
          container={container}
          variant="temporary"
          open={mobileOpen}
          onClose={handleDrawerToggle}
          ModalProps={{
            keepMounted: true, // Better open performance on mobile.
          }}
          sx={{
            display: { xs: 'block', sm: 'none' },
            '& .MuiDrawer-paper': { boxSizing: 'border-box', width: drawerWidth },
          }}
        >
          {drawer}
        </Drawer>
      </Box>
      
    </Box>
  );
}

DrawerAppBar.propTypes = {
  /**
   * Injected by the documentation to work in an iframe.
   * You won't need it on your project.
   */
  window: PropTypes.func,
};

export default DrawerAppBar;
