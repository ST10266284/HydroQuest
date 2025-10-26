using HydroQuestApi.Services;
using Microsoft.AspNetCore.Mvc;

namespace HydroQuestApi.Controllers
{
    [ApiController]
    [Route("api/gamification")]
    public class GamificationController : ControllerBase
    {
        private readonly GamificationService _gamificationService;

        public GamificationController(GamificationService gamificationService)
        {
            _gamificationService = gamificationService;
        }

        [HttpPost("update/{userId}/{amount}")]
        public async Task<IActionResult> UpdateGamification(string userId, int amount)
        {
            await _gamificationService.UpdateGamification(userId, amount);
            return Ok();
        }

        [HttpGet("{userId}")]
        public IActionResult GetGamification(string userId)
        {
            var game = _gamificationService.GetGamification(userId);
            if (game == null) return NotFound();
            return Ok(game);
        }
    }
}
