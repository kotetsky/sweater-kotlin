<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
<b>Add new user</b>
  ${message?ifExists}
  <@l.login "/registration" />
</@c.page>