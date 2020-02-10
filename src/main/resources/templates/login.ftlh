<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>


    You have visited HomeLib - your personal library
    ${message?if_exists}
    <@l.login "/login" false/>

</@c.page>