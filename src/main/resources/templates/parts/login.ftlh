<#macro login path isRegisterForm>
    <form action = "${path}"  method="post">
        <div class="form-group">
            <label class="col-sm-2 col-form-label"> User Name: </label>
            <div class="col-sm-6">
            <input type="text"
                   name="username"
                   class="form-control ${(usernameError??)?string('is-invalid','')}"
                   placeholder="Enter user name"
                   value = "<#if user??>${user.username}</#if>"
                   />

                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-6">
                <input class="form-control ${(passwordError??)?string('is-invalid','')}"
                       type="password"
                       name="password"
                       placeholder="Enter password"
                       <#if isRegisterForm>
                           value="<#if user??>${user.username}</#if>"
                       </#if>
                />

                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>

        <#if isRegisterForm>
            <div class="form-group">
                <label class="col-sm-2 col-form-label"> Confirm password: </label>
                <div class="col-sm-6">
                    <input class="form-control ${(password2Error??)?string('is-invalid','')}"
                           type="password"
                           name="password2"
                           placeholder="Retype password"/>

                    <#if password2Error??>
                        <div class="invalid-feedback">
                            ${password2Error}
                        </div>
                    </#if>
                </div>
            </div>

        <div class="form-group">
            <label class="col-sm-2 col-form-label"> Email: </label>
            <div class="col-sm-6">
                <input class="form-control ${(emailError??)?string('is-invalid','')}"
                       name="email"
                       placeholder="Enter email"
                       value="<#if user??>${user.email}</#if>"
                       />

                <#if emailError??>
                    <div class="invalid-feedback">
                        ${emailError}
                    </div>
                </#if>
            </div>
        </div>
        </#if>

        <#if !isRegisterForm>
            <a href="/registration">Sign up</a>
        </#if>

        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <div>
            <button type="submit"
            class="btn btn-primary mt-3">
                <#if isRegisterForm>Create<#else>Sign In</#if>
            </button>
        </div>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-primary">
            Sign out
        </button>
    </form>
</#macro>