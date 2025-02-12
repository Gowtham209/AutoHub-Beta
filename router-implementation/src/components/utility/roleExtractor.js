import  { jwtDecode } from 'jwt-decode';

function roleExtract()
{
    const jwt=localStorage.getItem('jwt');
    const decodedToken=jwtDecode(jwt);

    return decodedToken.role;
}

export default roleExtract