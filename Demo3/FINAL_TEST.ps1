# FINAL TEST SCRIPT
$baseUrl = "http://localhost:8080"

Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "FINAL LOGIN TEST" -ForegroundColor Cyan
Write-Host "========================================`n" -ForegroundColor Cyan

# Test 1: API Login
Write-Host "[1] Testing API Login..." -ForegroundColor Yellow
$apiBody = @{
    usernameOrEmail = "admin"
    password = "123456"
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri "$baseUrl/api/auth/signin" `
        -Method POST `
        -ContentType "application/json" `
        -Body $apiBody
    Write-Host "✅ API Login: $response" -ForegroundColor Green
} catch {
    Write-Host "❌ API Login Failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 2: Web Form Login
Write-Host "`n[2] Testing Web Form Login..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "$baseUrl/login" `
        -Method POST `
        -ContentType "application/x-www-form-urlencoded" `
        -Body "username=admin&password=123456" `
        -MaximumRedirection 0 `
        -ErrorAction SilentlyContinue
} catch {
    $location = $_.Exception.Response.Headers.Location
    
    if ($location -like "*/" -and $location -notlike "*/login*") {
        Write-Host "✅ Web Form Login SUCCESS! Redirect to: $location" -ForegroundColor Green
        Write-Host "`n🎉 LOGIN HOẠT ĐỘNG HOÀN HẢO!" -ForegroundColor Green
        Write-Host "`nBây giờ bạn có thể:" -ForegroundColor Cyan
        Write-Host "  1. Mở browser: http://localhost:8080/login" -ForegroundColor White
        Write-Host "  2. Username: admin" -ForegroundColor White
        Write-Host "  3. Password: 123456" -ForegroundColor White
    } elseif ($location -like "*/login?error=true") {
        Write-Host "❌ Web Form Login FAILED! Still showing errors" -ForegroundColor Red
        Write-Host "   Check application logs for details" -ForegroundColor Yellow
    }
}

Write-Host "`n========================================" -ForegroundColor Cyan
