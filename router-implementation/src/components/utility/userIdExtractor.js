import  { jwtDecode } from 'jwt-decode';

function userIdExtract()
{
    const jwt=localStorage.getItem('jwt');
    const decodedToken=jwtDecode(jwt);

    return decodedToken.userId;
}

export default userIdExtract