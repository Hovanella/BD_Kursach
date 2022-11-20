import React, { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import AuthenticationService from "../../Services/AuthenticationService";
import { useNavigate } from "react-router-dom";
import styles from "./RegisterPage.module.css";
import UserService from "../../Services/UserService";


const Register = () => {

        const navigator = useNavigate();
        const [logins, setLogins] = useState(["login"]);
        const {register, handleSubmit, formState: {errors},getValues,getFieldState} = useForm();

        useEffect(() => {

            const allLoginsFromServer = async () => {
                const response = await UserService.getAllLogins();
                setLogins(response.data);

            }
            allLoginsFromServer().catch(error => console.log(error));
        }, []);

        const handleRegister = (e) => {
            AuthenticationService.register(...getValues(["login","password","email"])).then(
                () => {
                    navigator("/home");
                },
                (error) => {
                }
            );

        };
        return (
            <div className={styles.fullScreenContainer}>
                <div className={styles.loginContainer}>
                    <h1 className={styles.loginTitle}>Welcome Stranger</h1>

                    <form onSubmit={handleSubmit(handleRegister)} className={styles.form}>


                        <div className={`${styles.inputGroup}  ${errors?.login ? styles.error : styles}`}>
                            <label htmlFor="login">Login</label>
                            <input type="text" name="login" id="login"  {...register("login", {
                                required: "Login is required",
                                validate: (value) => {
                                    if (logins.includes(value)) {
                                        return "Such login already exists";
                                    }
                                }
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


                        <div className={`${styles.inputGroup}  ${errors?.email ? styles.error : styles}`}>
                            <label htmlFor="email">Email</label>
                            <input type="email" name="email" id="email" {...register("email", {
                                required: "Email is required",
                                validate: (value) => {
                                    const regex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$/i;
                                    if (!regex.test(value)) {
                                        return "Invalid email";
                                    }
                                }
                            })}/>
                            <span className={styles.msg}>{errors?.email ? errors?.email.message : ""}</span>
                        </div>

                        <input type="submit" className={styles.registerButton} value="Enter the music"/>
                    </form>
                    {/*TODO MAKE REDIRECT LOGIN BUTTON */}
                </div>
            </div>
        );
    }
;
export default Register;