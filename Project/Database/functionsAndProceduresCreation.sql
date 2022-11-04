--please create oracle-function with password parameter and hash it with SHA256 and return it to the client.
CREATE OR REPLACE FUNCTION encrypt_password
    (password VARCHAR2)
    RETURN VARCHAR2
IS
    encryption_key VARCHAR2(2000) := '0710196810121972';
    encryption_pattern NUMBER := DBMS_CRYPTO.encrypt_aes128 + DBMS_CRYPTO.chain_cbc + DBMS_CRYPTO.pad_pkcs5;
    encryption_result RAW(2000);
BEGIN
    encryption_result := DBMS_CRYPTO.encrypt(utl_i18n.string_to_raw(password, 'AL32UTF8'), encryption_pattern, utl_i18n.string_to_raw(encryption_key, 'AL32UTF8'));
RETURN RAWTOHEX(encryption_result);
END encrypt_password;

    --create funtion that login user and return table with user's data

CREATE OR REPLACE FUNCTION Get_UserID_BY_PASSWORD_AND_LOGIN
    (login VARCHAR2, password VARCHAR2)
    RETURN SYS_REFCURSOR
IS
begin
    return SYS_REFCURSOR(open(SELECT * FROM users WHERE login = login AND password = encrypt_password(password)));
end Get_UserID_BY_PASSWORD_AND_LOGIN;

--create REGISTER_USER procedure that register user in database
CREATE OR REPLACE PROCEDURE REGISTER_USER
    (user_login VARCHAR2, user_password VARCHAR2, user_email VARCHAR2,user_role_id Number) is
begin
    INSERT INTO users (login, PASSWORD, email,ROLE_ID) VALUES (user_login, encrypt_password(user_password), user_email,user_role_id);
end REGISTER_USER;

begin
    REGISTER_USER('tesddt','test','dastut@by.com',3);
end;



