import { useEffect, useState } from "react";
import PopUp from '../../component/PopUp'
import { Container,Grid2,Button,Paper} from "@mui/material";
import UserDetailsForm from "../../forms/UserDetailForm";
import { getAllUsername } from "../../services/apiUserService";
import { DataGrid } from '@mui/x-data-grid';

const initialUserRegData={
    userDto:{
        username:"",
        firstName:"",
        lastName:"",
        email:""
    },
    password:"",
    userRoleDto:{
        roleName:""
    },
    userContactDto:[{
        contactNum:""
    }]
}

const columns = [
    { field: 'id', headerName: '#', width: 20 },
    { field: 'username', headerName: 'Username', width: 100 },
    { field: 'firstName', headerName: 'First name', width: 130 },
    { field: 'lastName', headerName: 'Last name', width: 130 },
    { field: 'email',headerName: 'Email',type: 'email',width: 130,},
    { field: 'role',headerName: 'Role',width: 120,},
];

const AllUsersView=()=>{
    let rows=[];
    const [users,setUsers]=useState([]);
    
    //control data loading asynchronus nature 
    const [isLoading, setIsLoading] = useState(false);

    //fetch all users data
    const loadAllUsers=()=>{
        setIsLoading(true)
        getAllUsername().then((response)=>{
            setUsers(response.data);
            rows = users.map((item,index)=>({
                    id: index,
                    username: item.userDto.username, 
                    lastName: item.userDto.lastname, 
                    firstName: item.userDto.firstName, 
                    email: item.userDto.email, 
                    role: item.userRoleDto.roleName
                }));
        }).finally(()=>{
            setIsLoading(false);
        })
    }

    useEffect(()=>{
        loadAllUsers();
        
    },[]);

    //use state for handle pop up open/close
    const [openPopup,setOpenPopup]=useState(false);

    const [userRegData,setUserRegData]=useState(initialUserRegData);

    const handleSubmit=()=>{

    }

    

    return(
        <Container sx={{pt:2}}>
            <Paper sx={{ height: "auto", width: '100%'}}>
            
            {!isLoading &&
                <DataGrid
                    rows={rows}
                    columns={columns}
                    //defines the intial page and intial page rows count
                    initialState={{ pagination: { page: 0, pageSize: 10 } }}
                    pageSizeOptions={[5, 10]}
                    checkboxSelection
                    sx={{ border: 0 }}
                />
            }
            </Paper>
            <Grid2 container spacing={2} justifyContent="end" sx={{my:2}}>
                <Grid2 size={{xs:"auto"}}>
                <Button variant="contained" color="primary" onClick={()=>setOpenPopup(true)}>
                    Add User
                </Button>
                </Grid2>
            </Grid2>
            
        <PopUp 
            tite="Add new user"
            openPopup={openPopup}
            children={<UserDetailsForm  onSubmit={handleSubmit} userUpdateData={userRegData} editMode={true} setUserUpdateData={setUserRegData}/>}
            setOpenPopup={setOpenPopup}/>
        </Container>
       
    );
}

export default AllUsersView;