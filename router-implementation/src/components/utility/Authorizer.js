import  { jwtDecode } from 'jwt-decode';


function authorize(Role)
{
    // Temp
    const jwt=localStorage.getItem('jwt');
    console.log(jwt);
    if(jwt==null)
    {
     console.log('No JWT token Present');
     alert('No JWT token Present');
     return false;
    // navigate('/unAuthorized');
    }

    // Checking the ROLE Match
    const decodedToken=jwtDecode(jwt)
    const role=decodedToken.role;
    if(Role!=role)
    {
     console.log("Role Doesn't MATCH");
     alert("Role Doesn't MATCH");
     return false;
     //navigate('/unAuthorized');
    }
    return true;

}

export default authorize;