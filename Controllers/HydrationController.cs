using HydroQuestApi.Services;
using Microsoft.AspNetCore.Mvc;

namespace HydroQuestApi.Controllers
{
    [ApiController]
    [Route("api/hydration")]
    public class HydrationController : ControllerBase
    {
        private readonly HydrationService _hydrationService;

        public HydrationController(HydrationService hydrationService)
        {
            _hydrationService = hydrationService;
        }

        [HttpPost("log")]
        public async Task<IActionResult> LogIntake([FromBody] LogIntakeDto dto)
        {
            await _hydrationService.LogIntake(dto.UserId, dto.Amount);
            return Ok();
        }

        [HttpGet("daily/{userId}")]
        public IActionResult GetDailyIntake(string userId)
        {
            var intake = _hydrationService.GetDailyIntake(userId);
            return Ok(intake);
        }

        [HttpGet("weekly/{userId}")]
        public IActionResult GetWeeklyIntake(string userId)
        {
            var today = DateTime.UtcNow.Date;
            var weekData = new List<int>();

            for (int i = 6; i >= 0; i--)
            {
                var day = today.AddDays(-i);
                var dailyIntake = _hydrationService.GetDailyIntakeForDate(userId, day);
                weekData.Add(dailyIntake);
            }

            return Ok(weekData);
        }

    }

    public class LogIntakeDto
    {
        public string UserId { get; set; } = string.Empty;
        public int Amount { get; set; }
    }
}
