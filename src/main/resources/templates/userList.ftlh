<#import "parts/common.ftlh" as c>
<@c.page>
<b>List of users</b><br/>
<table>
    <thead>
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th>Command</th>
        </tr>
    </thead>
    <tbody>
    <#list users as user>
        <tr>
            <td>${user.username}</td>
            <td><#list user.roles as role>${role}<#sep>, </#list></td>
            <td><a href="/user/${user.id}">edit user</a></td>
        </tr>
    </#list>
    </tbody>
</table>

</@c.page>