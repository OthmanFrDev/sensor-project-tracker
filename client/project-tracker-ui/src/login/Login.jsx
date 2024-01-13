import { Button } from 'primereact/button'
import React, { useState } from 'react'
import { InputText } from 'primereact/inputtext'
import { login } from '../resources/Api';
import { useNavigate } from 'react-router-dom';
import { jwtDecode } from 'jwt-decode'

function Login(props) {
    const [loginPayload, setLoginPayload] = useState({
        email: "",
        password: "",
        isValid: false
    });
    const [error, setError] = useState("");
    const navigate = useNavigate();
    const onSubmitHandler = (e) => {
        e.preventDefault();
        if (loginPayload.email?.length > 0 && loginPayload.password?.length > 0) {
            login(loginPayload).then(res => {
                const claims = jwtDecode(res?.data?.accessToken)
                localStorage.setItem('user_id', claims?.id);
                localStorage.setItem('email', claims.sub);
                localStorage.setItem('access_token', res.data.accessToken);
                navigate("/projects");
                setError(null)
            }).catch((err) => {
                console.log(err)
                setError(err?.message)
            })
        }

    }
    const onChange = (e) => {
        setLoginPayload({
            ...loginPayload,
            [e.target.name]: e.target.value,
            isValid: loginPayload.email?.length > 0 && loginPayload.password?.length > 0
        })
    }
    return (
        <div className="flex align-items-center justify-content-center">
            <div className="surface-card p-4 shadow-2 border-round w-full lg:w-6">
                <div className="text-center mb-5">
                    <div className="text-900 text-3xl font-medium mb-3">Se connecter</div>
                </div>

                <div>
                    <form onSubmit={onSubmitHandler}>
                        <label htmlFor="email" className="block text-900 font-medium mb-2">Email</label>
                        <InputText id="email" value={loginPayload?.email} type="text" name='email' onChange={onChange} placeholder="Email address" className="w-full mb-3" />

                        <label htmlFor="password" className="block text-900 font-medium mb-2">Mot de passe</label>
                        <InputText id="password" value={loginPayload?.password} type="password" name='password' onChange={onChange} placeholder="Password" className="w-full mb-3" />

                        {
                            error &&
                            <label htmlFor="password" className="block text-center font-medium mb-2" style={{ color: 'red' }}>{error}</label>
                        }
                        <Button label="Se connecter" icon="pi pi-user" className="w-full" disabled={!loginPayload.isValid} />
                    </form>
                </div>
            </div>
        </div>

    )
}
export default Login
