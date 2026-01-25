# Fix Spring Boot Configuration Error
# This script removes the invalid 'spring.profiles.active' property from profile-specific files

$devPropertiesPath = "src\main\resources\application-dev.properties"
$prodPropertiesPath = "src\main\resources\application-prod.properties"

Write-Host "Fixing Spring Boot configuration error..." -ForegroundColor Cyan

# Fix application-dev.properties
if (Test-Path $devPropertiesPath) {
    Write-Host "Processing $devPropertiesPath..." -ForegroundColor Yellow
    $content = Get-Content $devPropertiesPath -Raw
    
    # Remove lines containing spring.profiles.active
    $lines = Get-Content $devPropertiesPath | Where-Object { $_ -notmatch '^\s*spring\.profiles\.active' }
    
    $lines | Set-Content $devPropertiesPath
    Write-Host "✓ Removed 'spring.profiles.active' from $devPropertiesPath" -ForegroundColor Green
} else {
    Write-Host "! $devPropertiesPath not found" -ForegroundColor Red
}

# Fix application-prod.properties (just in case)
if (Test-Path $prodPropertiesPath) {
    Write-Host "Processing $prodPropertiesPath..." -ForegroundColor Yellow
    $content = Get-Content $prodPropertiesPath -Raw
    
    if ($content -match 'spring\.profiles\.active') {
        $lines = Get-Content $prodPropertiesPath | Where-Object { $_ -notmatch '^\s*spring\.profiles\.active' }
        $lines | Set-Content $prodPropertiesPath
        Write-Host "✓ Removed 'spring.profiles.active' from $prodPropertiesPath" -ForegroundColor Green
    } else {
        Write-Host "✓ $prodPropertiesPath is already correct" -ForegroundColor Green
    }
} else {
    Write-Host "! $prodPropertiesPath not found" -ForegroundColor Red
}

Write-Host "`nConfiguration fix complete!" -ForegroundColor Green
Write-Host "You can now run your Spring Boot application." -ForegroundColor Cyan
