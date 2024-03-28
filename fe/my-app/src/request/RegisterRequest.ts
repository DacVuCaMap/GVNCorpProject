class RegisterRequest {
    email:string;
    password:string;
    name:string;
    avatar:string;
    phonenumber:string;
    constructor(email:string,password:string,name:string,avatar:string,phoneNumber:string) {
        this.email=email;
        this.password=password;
        this.name=name;
        this.avatar=avatar;
        this.phonenumber=phoneNumber;
    }
    
}
export default RegisterRequest