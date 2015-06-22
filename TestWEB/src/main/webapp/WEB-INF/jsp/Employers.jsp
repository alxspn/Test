<%@ include file="/WEB-INF/jspx/Global.jsp" %>
<form:form method="POST" commandName="employee" action="Employers.html">
	<table id="employersTable"><tr><td>		
		<table>	
			<tr>
				<td>		
					<spring:message code="employee.label.name"/>
				</td>
				<td>		
					<spring:message code="employee.label.wage"/>
				</td>
			</tr>
		
			<tr>
				<td>
					<form:input path="name" style="width:450px" id="inputs"/>
				</td>
				<td>
					<form:input path="wage" style="width:115px" id="inputs"/>
				</td>				
			</tr>	
			<tr>
				<td colspan="2">
					<font size=1 color="red">
						<form:errors path="name"/>
					</font>
					<br>
					<font size=1 color="red">
						<form:errors path="wage"/>
					</font>					
				</td>
			</tr>	
			<tr>
				<td colspan="2">
					<form:select path="department" items="${departments}" itemValue="id"  itemLabel="name" id="inputs" style="width:565px"/>				
					<input id="buttons" type="submit" name="getDepartment" style="width:30px" value='<spring:message code="employee.departmentButton"/>' <c:if test="${(departments == null)}">disabled</c:if>>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<font size=1 color="red">
						<form:errors path="department"/>
					</font>					
				</td>
			</tr>								
		</table>

		<table>
			<tr>
				<td>
					<input id="buttons" type="submit" name="save" style="width:197px" value='<spring:message code="employee.saveButton"/>'>			
				</td>
				<td>
					<input id="buttons" type="submit" name="new" style="width:197px" <c:if test="${isNew}">disabled</c:if> value='<spring:message code="employee.newButton"/>'>			
				</td>		
				<td>
					<input id="buttons" type="submit" name="delete" style="width:197px" <c:if test="${isNew}">disabled</c:if> value='<spring:message code="employee.deleteButton"/>'>			
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<form:select multiple="true" size = "15" path="id" items="${employers}" itemValue="id"  itemLabel="fullName" id="inputs" style="width:600px" onClick="submit()" disabled="${employers == null}"/>				
				</td>
			</tr>
			<tr>
				<td colspan="3" align="center">
					<input id="buttons" type="submit" name="return" style="width:197px" value='<spring:message code="employee.returnButton"/>'>
				</td>
			</tr>	
		</table>
	</td></tr></table>	
</form:form>