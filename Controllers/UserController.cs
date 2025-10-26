using HydroQuestApi.Services;
using Microsoft.AspNetCore.Mvc;

namespace HydroQuestApi.Controllers
{
    [ApiController]
    [Route("api/user")]
    public class UserController : ControllerBase
    {
        private readonly UserService _userService;

        public UserController(UserService userService)
        {
            _userService = userService;
        }

        [HttpPost("auth")]
        public async Task<IActionResult> Auth([FromBody] string idToken)
        {
            var user = await _userService.VerifyAndGetUser(idToken);
            if (user == null) return BadRequest();
            return Ok(user);
        }

        [HttpPut("settings/{userId}")]
        public async Task<IActionResult> UpdateSettings(string userId, [FromBody] UpdateSettingsDto dto)
        {
            await _userService.UpdateSettings(userId, dto.Goal, dto.Units, dto.Theme);
            return Ok();
        }
    }

    public class UpdateSettingsDto
    {
        public int Goal { get; set; }
        public string Units { get; set; } = string.Empty;
        public string Theme { get; set; } = string.Empty;
    }
}
