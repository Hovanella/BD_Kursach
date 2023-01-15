import React, { useState, useRef } from "react";
import { useNavigate } from 'react-router-dom';
import AuthenticationService from "../../Services/AuthenticationService";
import styles from "../RegisterPage/RegisterPage.module.css";
import { useForm } from "react-hook-form";


const Login = () => {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();
    const {register, handleSubmit, formState: {errors}, getValues, getFieldState} = useForm();

    const onChangeUsername = (e) => {
        const username = e.target.value;
        setUsername(username);
    };

    const onChangePassword = (e) => {
        const password = e.target.value;
        setPassword(password);
    };


    const handleLogin = async (e) => {
        const isLoginSuccessful = await AuthenticationService.login(...getValues(["login", "password"]));
        if (!isLoginSuccessful) return;
        const isAdmin = (await AuthenticationService.isAdmin()).data;
        console.log(isAdmin);
        if (isAdmin) {
            navigate("/admin");
        } else {
            navigate("/home");
        }
        window.location.reload();


    };
    return (
        <div className={styles.fullScreenContainer}>
            <div className={styles.loginContainer}>
                <h1 className={styles.loginTitle}>Welcome Home</h1>

                <form onSubmit={handleSubmit(handleLogin)} className={styles.form}>

                    <div className={`${styles.inputGroup}  ${errors?.login ? styles.error : styles}`}>
                        <label htmlFor="login">Login</label>
                        <input type="text" name="login" id="login"  {...register("login", {
                            required: "Login is required",
                        })
                        }/>
                        <span className={styles.msg}>{errors?.login ? errors?.login.message : ""}</span>
                    </div>


                    <div className={`${styles.inputGroup}  ${errors?.password ? styles.error : styles}`}>
                        <label htmlFor="password">Password</label>
                        <input type="password" name="password" id="password" {...register("password", {
                            required: "Password is required"
                        })}/>
                        <span className={styles.msg}>{errors?.password ? errors?.password.message : ""}</span>
                    </div>

                    <input type="submit" className={styles.loginButton} value="Return to the world of music"/>

                    <a href="/register">Register</a>
                </form>
            </div>
        </div>
    );
};
export default Login;
