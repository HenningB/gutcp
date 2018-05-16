<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Ionization Chart</title>
</head>
<body>
<table>
<#list chartDatas as chartDataRow>
<tr>
<#list chartDataRow as chartData>
<#if chartData??>
<td style="background:${chartData.backgroundColor};">
${chartData.atomicSymbol}&nbsp;Z=${chartData.atomicNumber}&nbsp;ion=${chartData.ionCharge}<br/>
<#if chartData.ionizationMeasurement??>M:&nbsp;${chartData.ionizationMeasurement}<br/></#if>
<#if chartData.ionizationCalculation??>C:&nbsp;${chartData.ionizationCalculation}<br/></#if>
<#if chartData.error??>err:&nbsp;${chartData.error}<br/></#if>
</td>
</#if>
</#list>
</tr>
</#list>
</table>
</body>
</html>